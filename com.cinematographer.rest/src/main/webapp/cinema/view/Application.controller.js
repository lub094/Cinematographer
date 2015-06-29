jQuery.sap.require("sap.m.MessageBox");
jQuery.sap.require("sap.m.MessageToast");

sap.ui.controller("com.cinematographer.view.Application", {
	showScreenings: function(event) {
		this.getView()
			.byId("navContainer")
			.to(this.getView()
					.byId("screenings"));
	}
});