The only additional app setting you need to set on azure portal UI is
```
APPLICATIONINSIGHTS_ENABLE_AGENT=true
```
Also ensure your function is connected to any application insights so you can see there your custom app logs

The only thing you need to have preinstalled on your local machine is azure cli
1. create function app on azure portal
2. in `build.gradle.kts` adjust `resourceGroup` and `appName`
3. `az login`
4. `./gradlew azureFunctionsDeploy` from the project root