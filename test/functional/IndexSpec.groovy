import grails.plugin.geb.GebSpec
import geb.IndexPage
import geb.OptionsPage
/**
 * User: ben
 * Date: 19/06/11
 * Time: 16:43
 */
class IndexSpec extends grails.plugin.geb.GebSpec {

    def "index page test"() {
        when:
            go "/salary-calculator"
        then:
            assert at (IndexPage)
            $("title").text() == "Salary Calculator"
            $("h1", 0).text() == "Salary Calculator"
    }

    def "navigate to options page"() {
        when:
            $('a', 0).click()
        then:
            assert at (OptionsPage)
    }

    String getBaseUrl() {
        "http://localhost:8080"
    }

}
