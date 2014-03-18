package com.example.snippet

import net.liftweb.http.js._
import JE._
import JsCmds._
import jquery._
import JqJE._
import net.liftweb.http._
import net.liftweb.util.Helpers._
import scala.xml._
import java.util.Date

object MyCalendar
{
	def render =
	{
		val calendarOptions = JsObj("events" -> Str(AjaxRequest.requestUrl))
		
		"#add [onclick]" #> SHtml.ajaxCall(Str("addEvent"), addEvent) &
		"#calScript" #> Script(JqId("calendar") ~> JsFunc("fullCalendar", calendarOptions))
	}
	
	private def addEvent(s: String) = 
	{
		val add = JsObj(
			"title" -> Str("event "+(eventsVar.is.length+1)), 
			"start" -> (new Date).toString,
			"end" -> (new Date).toString,
			"allDay" -> JsTrue
		)
		eventsVar(eventsVar.is :+ add)
		
		println("EVENTS BEFORE REFETCH: " + eventsVar)
		
		JqId("calendar") ~> JsFunc("fullCalendar", "refetchEvents")
	}
} 

object AjaxRequest
{
	def requestUrl: String =
	{
		S.fmapFunc(S.SFuncHolder(getJson))
		{
			ajaxFunction => S.encodeURL(S.contextPath + "/" + LiftRules.ajaxPath + "?" + ajaxFunction)
		}
	}
	
	private def getJson(s: String) =
	{
		println("EVENTS DURING REFETCH: " + eventsVar)
		
		JsonResponse(JsArray(eventsVar.is))
	}
}

object eventsVar extends RequestVar[List[JsObj]](List(JsObj(
			"title" -> "event 1", 
			"start" -> (new Date).toString,
			"end" -> (new Date).toString,
			"allDay" -> JsTrue
		)))