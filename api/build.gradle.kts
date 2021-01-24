plugins {
    id("com.yelp.codegen.plugin") version "1.4.1"
}

generateSwagger {
    platform = "kotlin-coroutines"
    packageName = "com.example.swcook.front"
    inputFile = file("./api-client.yml")
    outputDir = file("./build/")
}
