package com.example.snippet

import net.liftweb.http.js._
import JE._
import JsCmds._
import jquery._
import JqJE._
import net.liftweb.http._
import net.liftweb.http.SHtml._
import net.liftweb.util.Helpers._
import scala.xml._
import java.util.Date
import net.liftweb.http.S._

object MyCalendar
{
	def render =
	{
		val calendarOptions = JsObj("events" -> JsVar("events"))
		"#add [onclick]" #> SHtml.ajaxInvoke(addEvent) &		
		"#calScript" #> Script(
			SetExp(JsVar("events"), JsArray(eventsVar.is)) & 
			JqId("calendar") ~> JsFunc("fullCalendar", calendarOptions)
		)
	}
	
	private def addEvent() = 
	{
		val add = JsObj(
			"title" -> Str("event "+(eventsVar.is.length+1)), 
			"start" -> (new Date).toString,
			"end" -> (new Date).toString,
			"allDay" -> JsTrue
		)
		eventsVar(eventsVar.is :+ add)
		
		JqId("calendar") ~> JsFunc("fullCalendar", "removeEventSource", JsVar("events")) &
        SetExp(JsVar("events"), JsArray(eventsVar.is)) &
        JqId("calendar") ~> JsFunc("fullCalendar", "addEventSource", JsVar("events")) &
		JqId("calendar") ~> JsFunc("fullCalendar", "refetchEvents")
	}
} 

object eventsVar extends RequestVar[List[JsObj]](List(JsObj(
			"title" -> "event 1", 
			"start" -> (new Date).toString,
			"end" -> (new Date).toString,
			"allDay" -> JsTrue
		)))