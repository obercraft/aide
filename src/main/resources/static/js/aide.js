angular.module('aide', [])
.service('aideService', function($http) {
	  this.getComments = function (subject) {
	      	return $http.get('/rest/comments/' + subject);
	  };    
	  
	  this.postComment = function (comment) {
	      	return $http.post('/rest/comments', comment);
	  };
	  
	
});

angular.module('aideData',[])
  .service('aideDataService', function($http, $log) {
  
	  var self = this;	    	
	        self.allDenizens = {};
	        self.chits = {};
	        self.chitClass = {};
	        self.chitType = {};
	        self.monsterResult = {};
	        self.denizens = {};
	        $http.get("/rest/display").then(function(result) {
	        	self.allDenizens = result.data.chart;
	        	self.chits = result.data.chits;
	        	self.denizens = result.data.denizens;
	        	for (var i=0; i < self.chits.length; i++) {
	        		var c = self.chits[i];
	        		self.chitClass[c.name] = c.type;
	        		if (!self.chitType[c.type]) {
	        			self.chitType[c.type] = new Array();
	        		}
	        		self.chitType[c.type].push(c);
	        	}
	        });
	        
        self.getChitClass = function(name) {
        	var args = name.split(/\ /);
        	return self.chitClass[args[0]];
        };
                     

        self.chitSelect = function (chit) {
        	if (chit !== undefined) {
        		chit.selected = !chit.selected;
        	}
        	var monsterResult = [];
        	var hasMountain = false;
        	var hasCaves = false;
        	var selectedChits = new Array();
        	for (var i = 0; i < self.chits.length; i++) {
            	if (chit.type == "Warning" && self.chits[i].type == "Warning" && !(chit.position == self.chits[i].position && chit.name == self.chits[i].name)) {
            		self.chits[i].selected = false;
            	}        		
        		if (self.chits[i].selected) {
        			selectedChits.push(self.chits[i]);
        		
    	    		if (self.chits[i].position === "C") {
    	    			hasCaves = true;
    	    		}
    	    		if (self.chits[i].position === "M") {
    	    			hasMountain = true;
    	    		}
        		}
        	}

        	var usedMonsters = {};
        	var usedChits ={}; 
        	for (var i = 0; i < 6; i++ ) {
        		monsterResult[i] = new Array();
        		var denizens = self.allDenizens[i];
        		
        		for (var j=0; j < selectedChits.length; j++) {
        			var selectedChit = selectedChits[j];    			
        			for (var k=0; k < denizens.length; k++) {
        				var denizen = denizens[k];    				
        				for (var l=0; l < denizen.chits.length; l++) {
        					var denizenChit = denizen.chits[l];
        					var found = false;
        					var chitName = selectedChit.name + (selectedChit.type === "Warning" ? " " + selectedChit.position : ""); 
        					if (denizenChit.indexOf(chitName) > -1) {    						        				
            					if (denizenChit.match(/\(M\)/) && hasMountain) {
            						found = true;
            					} else if (denizenChit.match(/\(C\)/) && hasCaves) {
            						found = true;
            					} else if (!denizenChit.match(/\(M\)/) && !denizenChit.match(/\(C\)/)) {
            						found = true;
            					}        					
            					var midx = i + ":" + k;
            					var cidx = i + ":" + j;
            					if (found && !usedMonsters[midx] ) {
            						monsterResult[i].push(denizen);
            						$log.debug("found " + denizen.denizen + " "+ denizen.count);
            						usedMonsters[midx] = true;
            						usedChits[cidx] = true;
            					}
            				}
    	
        				}

        			}
        		}
        	}
        	$log.debug(monsterResult);
        	self.monsterResult = monsterResult;
        };
        
    });


angular.module('AideDirective', ['aide', 'aideContant'])
.controller('Controller', ['$scope', function($scope, aideService) {
}])
.directive('aideBreadcrumb', function() {
  return {
    restrict: 'E',
    templateUrl: 'breadcrumb.html',
    scope: {
    	data: "="    	
    }
  };
})
.directive('aideComments', function(aideService, status) {
  return {
    restrict: 'E',
    templateUrl: 'comments.html',
    scope: {
    	subject: "="
    },
    link: function (scope, element, attrs) {
    	
    	var initComments = function() {
    		aideService.getComments(scope.subject).then(
    	
			function(result) {
				scope.comments = result.data;
				scope.text = '';
			},
			function(error) {
				console.log(error);
			});
    	};
    	scope.createComment = function(text) {
    		var comment = {
    				subject: scope.subject,
    				text: text
    		};
    		aideService.postComment(comment).then(
				function(result) {
					initComments();
				},
				function(error) {
					console.log(error);
				});
		};
		initComments();
		scope.auth = status.auth;
    }
  };	
})
;



angular.module('aideApp', ['ngNewRouter', 'aideApp.home', 'aideApp.denizens', 'aideApp.denizen', 'aideApp.score'])
  .controller('AppController', ['$router' ,'$http', '$scope', '$log', AppController]);

function AppController($router, $http, $scope) {
	$router.config([
	      { path: '/',  redirectTo: '/home' },
	      { path: '/home', component: 'home'},
	      { path: '/denizen/:id', component: 'denizen' },
	      { path: '/denizens', component: 'denizens' },
	      { path: '/score', component: 'score'}
	      ]);
	
};

angular.module('aideApp.home', ['aideData', 'AideDirective'])
.controller('HomeController', ['aideDataService', '$log', function (aideDataService, $log) {
	this.breadcrumb = ["Prowling"];
	this.data = aideDataService;
}]);


angular.module('aideApp.denizen', ['aideData', 'ngNewRouter'])
.controller('DenizenController', ['$routeParams', 'aideDataService', function ($routeParams, aideDataService) {	
	this.id = $routeParams.id;
	this.breadcrumb = ["Denizen", this.id];
	this.data = aideDataService;
}]);

angular.module('aideApp.denizens', ['aideData'])
.controller('DenizensController', ['aideDataService', function (aideDataService) {		
	this.breadcrumb = ["Denizen"];
	this.data = aideDataService;
}]);


angular.module('aideApp.score', ['aideData', 'aide'])
.controller('ScoreController', ['aideDataService', 'aideService', '$log', function (aideDataService, aideService, $log) {
	this.breadcrumb = ["Score"];
	this.victory = [
    		{ 
    			name: "Treasure",
    			points: 0,
    			recorded: 0,
    			multiply: 1			
    		},
    		{ 
    			name: "Spells",
    			points: 0,
    			recorded: 0,
    			multiply: 2			
    		},
    		{ 
    			name: "Fame",
    			points: 0,
    			recorded: 0,
    			multiply: 10			
    		},
    		{ 
    			name: "Notoriety",
    			points: 0,
    			recorded: 0,
    			multiply: 20		
    		},
    		{ 
    			name: "Gold",
    			points: 0,
    			recorded: 0,
    			multiply: 30			
    		}		
    	];

	this.calculateScore = function (i) {
		var d = this.victory[i];
		var score =  1 * d.recorded - d.multiply * d.points;
		score =  score < 0 ? 3 * score : score;
		var basic = 0;
		if (score >= 0) {
			basic = parseInt (score / d.multiply);
		} else {
			var b1 = score / d.multiply;
			var b2 = parseInt (score / d.multiply);
			basic = b1 == b2 ? b2 : b2 - 1;
		}
		var bonus = basic * d.points;
		var total = basic + bonus;
		return total;
	};

	this.calculateTotal = function () {
		var d = this.victory;
		var t = 0;
		for (var i=0; i< d.length; i++) {
			t += this.calculateScore(i);
		}
		return t;
	};
	
}]);




/*
function AppController($router)	{}

angular.module('app.detail', ['ngNewRouter'])
.controller('DetailController', ['$routeParams', function($scope, $http, $log) {
    $scope.allDenizens = {};
    $scope.chits = {};
    $scope.chitClass = {};
    $scope.chitType = {};
    $scope.monsterResult = {};
    $scope.denizens = {};

    $http.get("/rest/display").then(function(result) {
    	$scope.allDenizens = result.data.chart;
    	$scope.chits = result.data.chits;
    	$scope.denizens = result.data.denizens;
    	for (var i=0; i < $scope.chits.length; i++) {
    		var c = $scope.chits[i];
    		$scope.chitClass[c.name] = c.type;
    		if (!$scope.chitType[c.type]) {
    			$scope.chitType[c.type] = new Array();
    		}
    		$scope.chitType[c.type].push(c);
    	}
    });
    
    
    $scope.chitSelect = function (chit) {
    	if (chit !== undefined) {
    		chit.selected = !chit.selected;
    	}
    	var monsterResult = [];
    	var hasMountain = false;
    	var hasCaves = false;
    	var selectedChits = new Array();
    	for (var i = 0; i < $scope.chits.length; i++) {
    		if ($scope.chits[i].selected) {
    			selectedChits.push($scope.chits[i]);
    		
	    		if ($scope.chits[i].position === "C") {
	    			hasCaves = true;
	    		}
	    		if ($scope.chits[i].position === "M") {
	    			hasMountain = true;
	    		}
    		}
    	}

    	var usedMonsters = {};
    	var usedChits ={}; 
    	for (var i = 0; i < 6; i++ ) {
    		monsterResult[i] = new Array();
    		var denizens = $scope.allDenizens[i];
    		
    		for (var j=0; j < selectedChits.length; j++) {
    			var selectedChit = selectedChits[j];    			
    			for (var k=0; k < denizens.length; k++) {
    				var denizen = denizens[k];    				
    				for (var l=0; l < denizen.chits.length; l++) {
    					var denizenChit = denizen.chits[l];
    					var found = false;
    					var chitName = selectedChit.name + (selectedChit.type === "Warning" ? " " + selectedChit.position : ""); 
    					if (denizenChit.indexOf(chitName) > -1) {    						        				
        					if (denizenChit.match(/\(M\)/) && hasMountain) {
        						found = true;
        					} else if (denizenChit.match(/\(C\)/) && hasCaves) {
        						found = true;
        					} else if (!denizenChit.match(/\(M\)/) && !denizenChit.match(/\(C\)/)) {
        						found = true;
        					}        					
        					var midx = i + ":" + k;
        					var cidx = i + ":" + j;
        					if (found && !usedMonsters[midx] && !usedChits[cidx]) {
        						monsterResult[i].push(denizen);
        						$log.debug("found " + denizen.denizen + " "+ denizen.count);
        						usedMonsters[midx] = true;
        						usedChits[cidx] = true;
        					}
        				}
	
    				}

    			}
    		}
    	}
    	$log.debug(monsterResult);
    	$scope.monsterResult = monsterResult;
    };
    
  }]);
*/