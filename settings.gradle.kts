rootProject.name = "chesster"

include(":Spring:common")

include("Spring:Security:common-security")
include(":Spring:Security:auth-service")
include("Spring:Security:api-gateway")

include(":Spring:user-service")
include(":Spring:game-service")