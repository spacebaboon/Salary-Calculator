package geb

import geb.Page

abstract class GrailsPage extends Page {

    // To be overridden by subclasses
    static controller = null
    static action = null

    static at = {
        // delegate here is the original page _instance_ (i.e. the subclass)

        def expectedPageControllerName = delegate.class.controller
        if (expectedPageControllerName == null) {
            throw new IllegalStateException("${delegate.class} forgot to declare which controller it belongs to")
        }

        def expectedPageActionName = delegate.class.action
        if (expectedPageActionName == null) {
            throw new IllegalStateException("${delegate.class} forgot to declare which action it is")
        }

        def actualPageControllerName = controllerName
             assert actualPageControllerName == expectedPageControllerName

        assert actualPageActionName == expectedPageActionName

        true // at checkers must return true
    }

    static content = {
        pageId { $("meta", name: "pageId").@content }
        controllerName { pageId.split('\\.')[0] }
        actionName { pageId.split('\\.')[1] }
    }

}