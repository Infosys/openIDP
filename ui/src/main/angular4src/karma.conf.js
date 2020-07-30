// Karma configuration file, see link for more information
// https://karma-runner.github.io/0.13/config/configuration-file.html

module.exports = function (config) {
  config.set({
    basePath: ".",
    frameworks: ["jasmine", "@angular/cli"],
    plugins: [
      require("karma-jasmine"),
      require("karma-chrome-launcher"),
      require("karma-jasmine-html-reporter"),
      require("karma-coverage-istanbul-reporter"),
      require("karma-coverage"),
      require("@angular/cli/plugins/karma"),
    ],
    client: {
      clearContext: false, // leave Jasmine Spec Runner output visible in browser
    },
    files: ["src/app/**/*.ts"],
    /* mime: {
      "text/x-typescript": ["ts","tsx"]
    }, */
    coverageReporter: {
      dir: "coverage",
      type: "text",
      subdir: ".",
      // Would output the results into: ."/coverage/"
    },
    coverageIstanbulReporter: {
      reports: ["html", "lcovonly"],
      fixWebpackSourcePaths: true,
    },
    angularCli: {
      environment: "dev",
    },
    reporters: ["progress", "kjhtml", "coverage"],

    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ["Chrome"],
    singleRun: false,
  });
};
