package io.clemcasa.goout.presenter.modules.createAttestation

interface CreateAttestationView {
    fun onShowLoading()
    fun onPerformJSScript(jsScript: String)
}

interface CreateAttestationPresenter {
    fun attach(view: CreateAttestationView)
    
    fun fillForm()
    fun didClickOnCreate()
    
    fun setGroceriesCheckBox(checked: Boolean)
    fun setWalkCheckBox(checked: Boolean)
    fun setDoctorCheckBox(checked: Boolean)
}

class CreateAttestationPresenterImpl: CreateAttestationPresenter {
    private var view: CreateAttestationView? = null
    
    override fun attach(view: CreateAttestationView) {
        this.view = view
    }
    
    override fun fillForm() {
        view?.onPerformJSScript("${getElement("field-firstname")}.value = 'Clément';")
        view?.onPerformJSScript("${getElement("field-lastname")}.value = 'Casamayou';")
        view?.onPerformJSScript("${getElement("field-birthday")}.value = '26/01/1992';")
        view?.onPerformJSScript("${getElement("field-lieunaissance")}.value = 'Paris 14 eme';")
        view?.onPerformJSScript("${getElement("field-address")}.value = '86 avenue verdier';")
        view?.onPerformJSScript("${getElement("field-town")}.value = 'Montrouge';")
        view?.onPerformJSScript("${getElement("field-zipcode")}.value = '92120';")
    }
    
    override fun setGroceriesCheckBox(checked: Boolean) {
        view?.onPerformJSScript("${getElement("checkbox-courses")}.checked = $checked")
    }
    
    override fun setWalkCheckBox(checked: Boolean) {
        view?.onPerformJSScript("${getElement("checkbox-sport")}.checked = $checked")
    }
    
    override fun setDoctorCheckBox(checked: Boolean) {
        view?.onPerformJSScript("${getElement("checkbox-sante")}.checked = $checked")
    }
    
    private fun getElement(id: String): String = "document.getElementById('$id')"
    
    override fun didClickOnCreate() {
        view?.onShowLoading()
        view?.onPerformJSScript("document.getElementById(\"generate-btn\").click();")
    }
}