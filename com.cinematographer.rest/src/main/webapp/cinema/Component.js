jQuery.sap.declare("sap.ui.demo.myFiori.Component");

sap.ui.core.UIComponent.extend("com.cinematographer.Component", {

	createContent : function() {
		var oView = sap.ui.view({
			id : "app",
			viewName : "com.cinematographer.view.Application",
			type : "XML",
			viewData : { component : this }
		});

		var oModel = new sap.ui.model.json.JSONModel({});
		oView.setModel(oModel);

		return oView;
	}
});