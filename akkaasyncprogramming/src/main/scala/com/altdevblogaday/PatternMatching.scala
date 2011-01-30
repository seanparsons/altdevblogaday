package com.altdevblogaday

abstract class SpecialWeapon

case class NuclearBomb(val megatons: Int) extends SpecialWeapon

case class Player(val name: String, var x: Int, var y: Int) {
  var health: Int = 30
  var medikitLeft: Int = 2
  def canUseMedikit(useAmount: Int) = medikitLeft >= useAmount
}

abstract class GameRequest(val playerName: String)

case class ShotFired(override val playerName: String, val x: Int, val y: Int) extends GameRequest(playerName)

case class PlayerMoved(override val playerName: String, val x: Int, val y: Int) extends GameRequest(playerName)

case class MedikitUsed(override val playerName: String, val useAmount: Int) extends GameRequest(playerName)

case class SpecialWeaponUsed(override val playerName: String, val specialWeapon: SpecialWeapon) extends GameRequest(playerName)

abstract class GameResponse

case class HealthRestored(val health: Int) extends GameResponse

case class PlayerReachedDestination(val name: String, val x: Int, val y: Int) extends GameResponse

case class ShotMissed() extends GameResponse

case class InvalidToUseZeroMediKit() extends GameResponse

case class PlayersShot(val players: List[String]) extends GameResponse

case class InvalidRequest(val request: GameRequest) extends GameResponse

case class GameWon() extends GameResponse

case class Game(val players: List[Player]) {
  def handleRequest(gameRequest: GameRequest): GameResponse = {
    val player = players.find(listedPlayer => listedPlayer.name == gameRequest.playerName).get
    gameRequest match {
      case PlayerMoved(name, x, y) => {
        player.x = x
        player.y = y
        new PlayerReachedDestination(name, x, y)
      }
      case MedikitUsed(name, 0) => new InvalidToUseZeroMediKit()
      case MedikitUsed(name, useAmount) if (player.canUseMedikit(useAmount)) => {
        player.health += (useAmount * 5)
        player.medikitLeft -= useAmount
        new HealthRestored(player.health)
      }
      case SpecialWeaponUsed(name, NuclearBomb(megatons)) => {
        players.foreach(heavilyIrradiatedPlayer => heavilyIrradiatedPlayer.health -= (megatons * 5000))
        new GameWon()
      }
      case ShotFired(by, atX, atY) => {
        // Find all the players at the location of the shot.
        val playersHit = players.filter(potentialHit => potentialHit.x == atX && potentialHit.y == atY)
        // Take health off the players hit.
        playersHit.foreach(playerHit => playerHit.health -= 15)
        // If all the players other than the player taking the shot have less than zero health then said player wins.
        if (players.filter(potentiallyDeadPlayer => potentiallyDeadPlayer != player).forall(potentiallyDeadPlayer => potentiallyDeadPlayer.health <= 0)) new GameWon()
        else {
          // Should the shot have hit any other players, then indicate which players have been hit...
          if (players.size > 0) new PlayersShot(playersHit.map(playerHit => playerHit.name))
          // ...Otherwise someone was a terrible shot.
          else new ShotMissed()
        }
      }
      case _ => new InvalidRequest(gameRequest)
    }
  }

  def printlnRequestResult(gameRequest: GameRequest) = {
    println("Request = " + gameRequest + ", Result = " + handleRequest(gameRequest))
  }
}

object PatternMatching {

  def intMatch(number: Int): String = {
    return number match {
      case 1 => "This is number 1."
      case 2 => "This is number 2, it comes after 1."
      case _ => "This number is neither 1 nor 2."
    }
  }

  def positiveIntMatch(number: Int): String = {
    return number match {
      case positive: Int if positive >= 0 => positive + " is greater than or equal to zero."
      case negative: Int => negative + " is less than zero."
    }
  }
 
  def anyMatch(something: Any): String = {
    return something match {
      case text: String => text
      case number: Int => "This is the number " + number + "."
      case _ => "Unknown thing."
    }
  }

  def variableMatch(expectedNumber: Int, numberGiven: Int): String = {
    return numberGiven match {
      case `expectedNumber` => "Wahoo!"
      case _ => "Doh!"
    }
  }

  def cake: Unit = {
    println()
    println("Examples using intMatch:")
    println(intMatch(1))
    println(intMatch(2))
    println(intMatch(99))
    println()
    println("Examples using anyMatch:")
    println(anyMatch(12))
    println(anyMatch("Chocolate cake."))
    println(anyMatch(2.7d))
    println()
    println("Examples using variableMatch:")
    println(variableMatch(12, 20))
    println(variableMatch(12, 12))
    println()
    println("Examples using positiveIntMatch:")
    println(positiveIntMatch(-10))
    println(positiveIntMatch(0))
    println(positiveIntMatch(10))
    println()
    println("Game Example:")
    val sean = new Player("Sean", 5, 10)
    val mike = new Player("Mike", 5, 5)
    val game = new Game(List(sean, mike))
    game.printlnRequestResult(new PlayerMoved("Sean", 2, 10))
    game.printlnRequestResult(new ShotFired("Mike", 5, 10))
    game.printlnRequestResult(new ShotFired("Sean", 5, 5))
    game.printlnRequestResult(new PlayerMoved("Mike", 2, 2))
    game.printlnRequestResult(new ShotFired("Mike", 2, 10))
    game.printlnRequestResult(new MedikitUsed("Sean", 0))
    game.printlnRequestResult(new MedikitUsed("Mike", 1))
    game.printlnRequestResult(new ShotFired("Sean", 2, 2))
    game.printlnRequestResult(new MedikitUsed("Mike", 2))
    game.printlnRequestResult(new SpecialWeaponUsed("Sean", new NuclearBomb(20)))
    println()
  }
}
