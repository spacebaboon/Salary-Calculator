package geb

/**
 * User: ben
 * Date: 24/06/11
 * Time: 17:10
 */
class IndexPage extends GrailsPage {
    static controller = 'index'
    static action = 'index'

    static content = {
        optionsLink { $('a', id: 'optionsLink') }
        
    }
}
