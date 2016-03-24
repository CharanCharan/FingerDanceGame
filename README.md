# FingerDanceGame

Game Rules:
- The board contains n*n different colored tiles.
- At the start, the user selects the value of N
- The game starts with a random F-tile highlighted.
- The player would touch and hold the highlighted F-tile, after that next random tile would automatically get highlighted.
- The 2nd player would touch and hold the next highlighted tile
- Goes on unitl


GAME OVER:
- 	The player clicks on the wrong tile
- 	The player lifts his finger from previously selected tile 


Additional rules :
- Find the maximum number of touch(T) allowed by a device.
- Tell the player the (F)maximum no. of fingers per person he can hold onto the device at a time. F =T/2

 
Design Flow Chart:
![alt text](https://github.com/CharanCharan/FingerDanceGame/blob/master/DesignFlow.jpg "Design Flow")

DESIGN APPROACH

Activities Overview:
- 	Application starts with an activity asking player to enter a number -> AskNValueActivity.java
- 	When player clicks start button, a new activity with a single fragment is launched
- 	Activity is TilesActivity.java (which extends SingleFragmentActivity.java) and fragment is TileGridFragment.java 
- 	When a player makes a mistake in the game, game is over and GameResultActivity.java is launched

Backend Flow:
- 	The user input (N) taken from AskNValueActivity.java is passed to TilesActivity.java 
- 	TilesActivity.java in turn passes the value to TileGridFragment.java
- 	TileGridFragment.java stores this value. It has a RecyclerView layout. It has a Referee to supervise the game when players start playing.
- 	Referee is an object of Singleton class Referee.java. Referee supervises the game, he calls up players, processes an event in the game, alternates player turns and tells when game is over.
- 	Referee calls players from Player.java class. Each player has a name and he can remember the tiles he pressed while playing the game. 
- 	Tiles come from Tile.java class. Each tile can have a unique color and can highlight themselves when needed.
- 	Now going back to RecyclerView of TileGridFragment.java, it has an Adapter which in turn has a  ViewHolder.  This ViewHolder has a button and when given a tile, it stashes that tile and it’s button together. Adapter takes a list of tiles from Tiles.java and passes each one of them to ViewHolder and asks RecyclerView to display each button with tile’s properties.
- 	Everything is setup and players are ready to play the game. Whenever a player touches a 
- 	Button ( tile ), Referee is notified to process the event and this continues.
- 	If a players breaks a rule, referee stops the game since game is finished. He keeps track of winner and loser of that round.
- 	When game is over, TileGridFragment.java starts a new activity i.e., GameResultActivity.java , and passes on the game result ( winner and loser names taken from Referee)  to it.
- 	GameResultActivity.java displays the winner and loser name and a button to start again.

Pending Tasks:
- 	Make use of number of touches supported by the device. I have written some code and commented it with ignore captions.
- 	My idea is to use a Queue to store the tiles to each player. When a player has reached the limit of fingers he can use ( number of touches supported/2), the player has to lift the finger from the tile which he used first among all his fingers being used.
- 	Then a tile from Queue is popped and if this tile is same as the tile from which the player has lifted his finger, he has done no mistake and game continues.
- 	If he lifts the wrong finger, then game is over.
