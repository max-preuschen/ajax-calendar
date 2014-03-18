package bootstrap.liftweb

import net.liftweb.http._
import net.liftweb.common.Full
import net.liftweb.sitemap._

class Boot {

  def boot {
    // where to search snippet
    LiftRules.addToPackages("com.example")

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart = Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd = Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))

    LiftRules.setSiteMapFunc(() => MenuInfo.sitemap)
  }

  object MenuInfo {
    def sitemap = SiteMap(Menu("Home") / "index")
  }
}