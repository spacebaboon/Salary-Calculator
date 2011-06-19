import grails.plugin.geb.GebSpec
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
            $("title").text() == "Salary Calculator"
            $("h1", 0).text() == "Salary Calculator"
    }

    String getBaseUrl() {
        "http://localhost:8080"
    }

}
