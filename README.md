# slot-machine-javafx
<h1>A JavaFX based GUI Application</h1>
<p>
This is a JavaFX based GUI Application - A Slot Machine. <br />
This Application allows the user to bet a certain number of credits and spin the wheels. If atleast two wheels
match, the user wins. The winning credits are calculated as: Value of Item displayed in the Wheel * Number of Credits bet. <br>
</p>

<br><br>

<h2>The Features</h2>
<ul>
    <li> Bet any number of credits (As long as you have that number of credits) </li>
    <li> Bet 3 credits at once by clicking on the Bet Max button </li>
    <li> Add Unlimited Credits to your Credits </li>
    <li> Reset the Bet Amount whenever required <li>
    <li> Spin the Wheels when you have credits in the Bet Amount </li>
    <li> Ability to Stop one wheel at an instance </li>
    <li> View Statistics (Only after playing atleast one game) </li>
    <li> Save Statistics to a file (Name of the File: Date and Time) </li>
</ul>

<br><br>

<h2>The Symbols</h2>
<ul>
    <li> 
        Cherry | Value: 2 
    </li>
    <li> 
        Lemon | Value: 3 
    </li>
    <li> 
        Plum | Value: 4 
    </li>
    <li> 
        Melon | Value: 5 
    </li>
    <li> 
        Bell | Value: 6 
    </li>
    <li> 
        RedSeven | Value: 7 
    </li>
</ul>

<br><br>

<h2>Screenshots of the Application</h2>

<br><br>

<h2>How does it work?</h2>

<p>
This JavaFX Application is a multithreaded application. Each Wheel runs on a different thread to produce a spinning illusion. The user will be able to stop each wheel individually. Stopping all three wheels ends the game and thus all the three threads stop. The Main thread on which the JavaFX application runs all the time. <br />
The List used to store the symbols that are displayed on the wheels are shuffled everytime to ensure that the Symbols are
displayed in a random order all the time. <br />
</p>