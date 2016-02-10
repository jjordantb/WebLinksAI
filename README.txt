Note this runs off of actual website url's NOT from files,
as that would require some modifications to href values in html.
Due to running on actual websites and using breadth-first search
it is really slow, a better implementation would have been to build
a bidirectional web of nodes and then apply a search algorithm to that
instead of building a large tree, but I ran out of time.

Example urls:
    start: http://www.uleth.ca
    end:   http://www.uleth.ca/bookstore

To compile
1. Navigate to the src folder
2. Run the command: javac *.java

To run
1. Ensure your in the src folder
2. Run the command: java Application
