// Karma configuration file, see link for more information
// https://karma-runner.github.io/0.13/config/configuration-file.html

module.exports = function (config) {
  config.set({
    basePath: "_"
    frameworks: ["jasmine", "@angular/cli"],
    plugins: [
      require("karma-jasmine"),
      require("karma-chrome-launcher"),
      require("karma-jasmine-html-reporter"),
      require("karma-coverage-istanbul-reporter"),
	  require("karma-coverage"),
      require("@angular/cli/plugins/karma"),
	 
    ],
    client:{
      clearContext: false // leave Jasmine Spec Runner output visible in browser
    },
	files: [
			/* "./src/assets/vendors/angularjs/angular.js",
			"./src/assets/vendors/angularjs/angular-mocks.js",
			"./src/assets/vendors/angularjs/angular-resource.js",
			"./src/assets/vendors/angularjs/angular-cookies.js",
			"./src/assets/vendors/angularjs/angular-route.js",
			"./src/assets/vendors/angularjs/jcs-auto-validate.js",
			"./src/assets/vendors/angularTranslate/angular-translate.js",
			"./src/assets/vendors/angularTranslate/angular-sanitize.js",
			"./src/assets/vendors/angularTranslate/angular-translate-storage-cookie.js",
			"./src/assets/vendors/angularTranslate/angular-translate-loader-static-files.js",
			"./src/assets/vendors/angularTranslate/angular-local-storage.js",
			"./src/assets/vendors/jquery.min.js", */
			"node_modules/crypto-js/crypto-js.js",
			// paths to support debugging with source maps in dev tools
      {pattern: "src/**/*.ts", included: false, watched: false}
            
        ],
	/* mime: {
      "text/x-typescript": ["ts","tsx"]
    }, */
	coverageReporter: {
	dir: "coverage",
	type : "text",
	subdir: "."
  // Would output the results into: ."/coverage/"
	},
    coverageIstanbulReporter: {
      reports: [ "html", "lcovonly" ],
      fixWebpackSourcePaths: true
    }, 
    angularCli: {
      environment: "dev"
    },
    reporters: ["progress", "kjhtml", "coverage"],
	
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ["Chrome"],
    singleRun: false
  });
};

