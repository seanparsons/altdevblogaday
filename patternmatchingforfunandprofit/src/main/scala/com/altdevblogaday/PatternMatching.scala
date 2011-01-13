case class Player(val name: String, var x: Int, var y: Int, var health: Int)

abstract class GameRequest

case class ShotFired(val by: String, val atX: Int, val y: Int) extends GameRequest

case class PlayerMoved(val name: String, val x: Int, val y: Int) extends GameRequest

abstract class GameResponse

case class PlayerReachedDestination(val name: String, val x: Int, val y: Int) extends GameResponse

case class ShotMissed() extends GameResponse

case class PlayersShot(val players: List[String]) extends GameResponse

case class GameWon() extends GameResponse

object PatternMatching {

  def intMatch(number: Int): String = {
    return number match {
      case 1 => "This is number 1."
      case 2 => "This is number 2, it comes after 1."
      case _ => "This number is neither 1 nor 2."
    }
  }
 
  def anyMatch(something: Any): String = {
    return something match {
      case text: String => text
      case number: Int => "This is the number " + number + "."
      case _ => "Unknown thing."
    }
  }

  def handleRequest(gameRequest: GameRequest, players: List[Player]): GameResponse = gameRequest match {
    case PlayerMoved(name, x, y) => {
      players
        .filter(player => player.name == name)
        .foreach{player =>
          player.x = x
          player.y = y
        }
      new PlayerReachedDestination(name, x, y)
    }
    case ShotFired(by, atX, atY) => {
      val playerFiringShot = players.find(player => player.name == by).get
      val playersHit = players.filter(player => player.x == atX && player.y == atY)
      playersHit.foreach(player => player.health = player.health - 15)
      
      if (players.filter(player => player != playerFiringShot).forall(player => player.health < 0)) new GameWon()
      else {
        if (players.size > 0) new PlayersShot(playersHit.map(player => player.name))
        else new ShotMissed()
      }
    }
  }
  
  def main(args: Array[String]): Unit = {
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
    println("Game Example:")
    val sean = new Player("Sean", 5, 10, 40)
    val mike = new Player("Mike", 5, 5, 20)
    val players = List(sean, mike)
    println("Sean moves: " + handleRequest(new PlayerMoved("Sean", 2, 10), players))
    println("Mike fires: " + handleRequest(new ShotFired("Mike", 5, 10), players))
    println("Sean fires: " + handleRequest(new ShotFired("Sean", 5, 5), players))
    println("Mike moves: " + handleRequest(new PlayerMoved("Mike", 2, 2), players))
    println("Mike fires: " + handleRequest(new ShotFired("Mike", 2, 10), players))
    println("Sean fires: " + handleRequest(new ShotFired("Sean", 2, 2), players))
    println()
  }
}
