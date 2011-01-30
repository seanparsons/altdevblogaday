package com.altdevblogaday

import akka.actor.Actor
import akka.actor.Actor._

class GameActor(val players: List[Player]) extends Actor {
  def receive = {
    case PlayerMoved(name, x, y) => {
      val player = lookupPlayer(name)
      player.x = x
      player.y = y
      self.reply_?(new PlayerReachedDestination(name, x, y))
    }
    case MedikitUsed(name, 0) => self.reply(new InvalidToUseZeroMediKit())
    case MedikitUsed(name, useAmount) if (lookupPlayer(name).canUseMedikit(useAmount)) => {
      val player = lookupPlayer(name)
      player.health += (useAmount * 5)
      player.medikitLeft -= useAmount
      self.reply_?(new HealthRestored(player.health))
    }
    case SpecialWeaponUsed(name, NuclearBomb(megatons)) => {
      players.foreach(heavilyIrradiatedPlayer => heavilyIrradiatedPlayer.health -= (megatons * 5000))
      self.reply_?(new GameWon())
    }
    case ShotFired(name, atX, atY) => {
      val player = lookupPlayer(name)
      // Find all the players at the location of the shot.
      val playersHit = players.filter(potentialHit => potentialHit.x == atX && potentialHit.y == atY)
      // Take health off the players hit.
      playersHit.foreach(playerHit => playerHit.health -= 15)
      // If all the players other than the player taking the shot have less than zero health then said player wins.
      if (players.filter(potentiallyDeadPlayer => potentiallyDeadPlayer != player).forall(potentiallyDeadPlayer => potentiallyDeadPlayer.health <= 0)) self.reply_?(new GameWon())
      else {
        // Should the shot have hit any other players, then indicate which players have been hit...
        if (players.size > 0) self.reply_?(new PlayersShot(playersHit.map(playerHit => playerHit.name)))
        // ...Otherwise someone was a terrible shot.
        else self.reply_?(new ShotMissed())
      }
    }
    case gameRequest: GameRequest => self.reply_?(new InvalidRequest(gameRequest))
    case _ => throw new RuntimeException("Bad things have happened.")
  }

  private[this] def lookupPlayer(playerName: String): Player = {
    return players.find(listedPlayer => listedPlayer.name == playerName).get
  }
}