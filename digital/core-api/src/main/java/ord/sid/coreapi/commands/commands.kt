package ord.sid.coreapi.commands

abstract class BaseCommand<T>(
        open val  id : T
)

data class CreateCustomerCommand(
        override val id :String,
        val name :String,
        val email : String
):BaseCommand<String>(id)

data class UpdateCustomerCommand(
        override val id :String,
        val name :String,
        val email : String
):BaseCommand<String>(id)