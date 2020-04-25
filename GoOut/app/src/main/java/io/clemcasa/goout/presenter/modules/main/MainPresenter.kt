package io.clemcasa.goout.presenter.modules.main

interface MainView {
    fun onShowAndUpdateList()
    fun onShowEmptyList()
}

interface MainPresenter {
    fun attach(view: MainView)
    fun setup()
    fun refreshData()
}

class MainPresenterImpl: MainPresenter {
    private var view: MainView? = null
    
    override fun attach(view: MainView) {
        this.view = view
    }
    
    override fun setup() {
        refreshData()
    }
    
    override fun refreshData() {
        view?.onShowAndUpdateList()
    }
}