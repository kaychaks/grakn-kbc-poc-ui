
import sangria.renderer.SchemaRenderer
import sangria.schema._

case class Schema1(f: String) {
  def someFn(xs: List[String]): List[String] = ???
}

val arg = Argument("inp",ListInputType(StringType))

val gSchema1 = ObjectType(
  "schema1",
  fields[Unit, Schema1](
    Field("someFn",ListType(StringType),
      arguments = List(arg),
      resolve = ctx => ctx.value.someFn(ctx.arg(arg).toList)
      )
  ))
val schema = Schema(gSchema1)
SchemaRenderer.renderSchema(schema)


