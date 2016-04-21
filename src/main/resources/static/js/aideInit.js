angular.module('aideInit',[])
  .service('data', function($http, $log) {
  
	  var self = this;	    	
	        self.allDenizens = {};
	        self.chits = {};
	        self.chitClass = {};
	        self.chitType = {};
	        self.monsterResult = {};
	        self.denizens = {};
	        $http.get("/rest/data").then(function(result) {
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
