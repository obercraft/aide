angular.module('aide', [])
.service('aideService', function($http) {
	  this.getComments = function (subject) {
	      	return $http.get('/rest/comments/' + subject);
	  };    
	  
	  this.postComment = function (comment) {
	      	return $http.post('/rest/comments', comment);
	  };	  
});

angular.module('aideDirective', ['aide', 'aideContant'])
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



angular.module('aideApp', ['ngNewRouter', 'aideInit', 'aideApp.home', 'aideApp.login', 'aideApp.register', 'aideApp.prowling', 'aideApp.denizens', 'aideApp.denizen', 'aideApp.score', 'aideApp.links'])
  .controller('AppController', ['$router' ,'$http', '$scope', '$log', AppController]);

function AppController($router, $http, $scope) {
	$router.config([
	      { path: '/',  redirectTo: '/home' },
	      { path: '/home', component: 'home'},
	      { path: '/login', component: 'login'},
	      { path: '/register', component: 'register'},
	      { path: '/profile', component: 'profile'},
	      { path: '/prowling', component: 'prowling'},
	      { path: '/denizen/:id', component: 'denizen' },
	      { path: '/denizens', component: 'denizens' },
	      { path: '/score', component: 'score'},
	      { path: '/links', component: 'links'}
	      ]);
	
};

angular.module('aideApp').run(['$rootScope', '$http', 'data', function ($rootScope, $http, data) {
	console.log("init");
	$rootScope.data = data;
}]);

angular.module('aideApp.home', ['aideDirective', 'aideContant'])
.controller('HomeController', ['$log', 'status', function ($log, status) {
	this.breadcrumb = ["Home"];	
	this.version = status.version;
}]);

angular.module('aideApp.login', ['aideDirective'])
.controller('LoginController', ['$log', '$location', function ($log, $location) {
	var  params = $location.search();
	console.log(params)
	this.error = params['error'] ? true:false;
	this.breadcrumb = ["Login"];	
}]);

angular.module('aideApp.register', ['aideDirective'])
.controller('RegisterController', ['$log', function ($log) {
	this.breadcrumb = ["Register"];	
}]);

angular.module('aideApp.prowling', ['aideDirective'])
.controller('ProwlingController', ['$log', function ($log) {
	this.breadcrumb = ["Prowling"];	
}]);


angular.module('aideApp.denizen', ['aideDirective', 'ngNewRouter'])
.controller('DenizenController', ['$routeParams', function ($routeParams) {	
	this.id = $routeParams.id;
	this.breadcrumb = ["Denizen", this.id];
}]);

angular.module('aideApp.denizens', [])
.controller('DenizensController', ['$routeParams', function ($routeParams) {		
	this.breadcrumb = ["Denizen"];
}]);

angular.module('aideApp.links', [])
.controller('LinksController', ['$routeParams', function ($routeParams) {		
	this.breadcrumb = ["Links"];
}]);


angular.module('aideApp.score', ['aide'])
.controller('ScoreController', ['aideService', '$log', function (aideService, $log) {
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
