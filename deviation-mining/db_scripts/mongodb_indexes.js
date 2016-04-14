db.registroEntrada.createIndex( { idEntrada: 1 }, { unique: true } )
db.registroEntrada.createIndex( { idUsuario: 1 } )
db.registroEntrada.createIndex( { dataEntrada: 1 } )
db.registroEntrada.createIndex( { sistema: 1 } )