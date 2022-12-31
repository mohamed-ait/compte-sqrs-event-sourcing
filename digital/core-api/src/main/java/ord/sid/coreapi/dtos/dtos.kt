package ord.sid.coreapi.dtos

data class CreateCustomerRequestDTO(
        var name:String="",
        var email:String=""
)
data class UpdateCustomerRequestDTO(
        var name:String="",
        var email:String=""
)