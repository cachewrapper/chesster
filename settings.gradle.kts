rootProject.name = "chesster"

include(":React:chesster-frontend")

include(":Spring:common")

include("Spring:Security:common-security")
include(":Spring:Security:auth-service")
include("Spring:Security:api-gateway")

include(":Spring:Game:game-service")

include(":Spring:user-service")
include("Spring:Game:game")