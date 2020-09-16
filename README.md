# Carcassonne
A java and Swing implementation of [Carcassonne](https://en.wikipedia.org/wiki/Carcassonne_(board_game))<sup>TM</sup> —tile-based board game for two to five players with the goal of chaining regions of adjacent tiles. The rules of Carcassonne can be found [here](https://github.com/v-machine/carcassonne/blob/master/design_documents/rules.pdf).

![](/demo/01_multi_players.gif)

## Design Rationale
The overall implementation follows the conventional Model-View-Controller (MVC) framework, where the model is responsible for the core logic of the game; the controller receives requests from users and modifies the model and the view renders the model data as graphical elements.

The design of the object model follows closely the abstraction level of domain models in order to minimize the representational gap. Using the principle of encapsulation, each object has its internal data structure and an API for modifying those data. To account for various completion checking and scoring mechanisms for different features, the design employs a [strategy design pattern](https://en.wikipedia.org/wiki/Strategy_pattern). Each feature type implements a common feature interface with different checking and scoring algorithms under the hood. The use of strategy pattern also decouples each features and accomodates extensibility when new features are to be added.

## Development Focus
- A comprehensive development process of object-oriented analysis, design and implementation.
- Practice articulating design goals, iterate design choices and use design patterns as appropriate. 
- Write testable core logic and a separate GUI (following MVC architecture).
- Demonstrate understanding of event handling in GUI implementation. 
- Communicate design ideas clearly and affectly using UML diagrams and behavioral specifications.

## Design Document
A set of design documents demostrating object-oriented design for the game core using UML design notations.
- the domain model
- the system sequence diagram
- behavioral contracts for playing a tile
- object-level interaction diagrams
- object model

![](/design_documents/jpgs/design_documents.jpg)
![](/design_documents/jpgs/design_documents2.jpg)
![](/design_documents/jpgs/design_documents3.jpg)
![](/design_documents/jpgs/design_documents4.jpg)
![](/design_documents/jpgs/design_documents5.jpg)
![](/design_documents/jpgs/design_documents6.jpg)
