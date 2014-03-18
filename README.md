ajax-calendar
=============

FullCalendar example with Lift AJAX and RequestVar

start example:

./sbt
container:start

open localhost:8080

click on "Add Event" 

This adds an new event (JsObj) to the RequestVar (eventsVar) and should be visible 
in the calendar immediately. (but unfortunately is not)
