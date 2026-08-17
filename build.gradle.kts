
plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

// Задача 1: Запускает все тесты в проекте
tasks.register("runAllTests") {
    description = "Запускает все тесты в проекте"
    group = "verification"
    dependsOn(tasks.test)
}

// Задача 2: После прогона тестов пишет в консоль Test run is over
// Запускается после завершения первой задачи
tasks.register("printMessage") {
    description = "Выводит сообщение после завершения тестов"
    group = "verification"
    dependsOn("runAllTests") // Зависимость от первой задачи

    doLast {
        println("Test run is over")
    }
}
