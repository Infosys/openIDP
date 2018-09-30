// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: true,
  authmode: "ldap",
   adalConfig: {
    tenant: "058aa436-9042-4335-86f1-5281c4247f2b",
    clientId: "1501f18d-15e1-4423-ac40-cca4be910f11",
    postLogoutRedirectUri: "http://localhost:4200/logout",
    endpoints: {
        "https://adaltestapi.azurewebsites.net": "https://adaltestapi.azurewebsites.net",
    },
  }
};
