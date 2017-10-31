(function() {

	var WarehouseApp = angular.module("WarehouseApp", ["ui.router"]);

	var WarehouseSvc = function($rootScope) {
		this.socket = null;
		this.isConnected = function() {
			return (!!this.socket);
		};
		this.connect = function(ws) {
			this.socket = new WebSocket(ws);
                        //this.socket.send('Wtf Man!! Remove your own impl in line 12 in index.js');
			this.socket.onmessage = function(msg) {
				$rootScope.$apply(function() {
					var data = JSON.parse(msg.data);
                                        //var data = msg.data;
                                        //alert(msg.data);
					data.recvTimestamp = (new Date()).toString();
					$rootScope.$broadcast("newDO", data);
				});
			};
		};
		this.disconnect = function() {
			if (!this.isConnected()) 
				return;
			this.socket.close();
			this.socket = null;
		};
	};

	var WarehouseConfig = function($stateProvider, $urlRouterProvider) {
		$stateProvider

			.state("main", {
				url: "/main",
				templateUrl: "views/main.html",
				controller: "MainCtrl as mainCtrl" })

			.state("incoming", {
				url: "/incoming",
				templateUrl: "views/incoming.html",
				controller: "IncomingDOCtrl as incomingDOCtrl" });

		$urlRouterProvider.otherwise("/main");
	};

	var WarehouseCtrl = function() { };
	var MainCtrl = function($state, WarehouseSvc) {
		var mainCtrl = this;
		mainCtrl.wsUrl = "ws://localhost:8080/TestWebSockets/fruits";
		mainCtrl.connect = function() {
			WarehouseSvc.disconnect();
			WarehouseSvc.connect(mainCtrl.wsUrl);
			$state.go("incoming");
		};
	};
	var IncomingDOCtrl = function($scope, $state, WarehouseSvc) {
		var incomingDOCtrl = this;
		incomingDOCtrl.doList = [];
		$scope.$on("newDO", function(_, newDO) {
			incomingDOCtrl.doList.unshift(newDO);
		});

		incomingDOCtrl.disconnect = function() {
			WarehouseSvc.disconnect();
			$state.go("main");
		};
	};
	WarehouseApp.service("WarehouseSvc", ["$rootScope", WarehouseSvc]);

	WarehouseApp.config(["$stateProvider", "$urlRouterProvider", WarehouseConfig]);

	WarehouseApp.controller("WarehouseCtrl", [WarehouseCtrl]);
	WarehouseApp.controller("MainCtrl", ["$state", "WarehouseSvc", MainCtrl]);
	WarehouseApp.controller("IncomingDOCtrl", ["$scope", "$state", "WarehouseSvc", IncomingDOCtrl]);

})();

