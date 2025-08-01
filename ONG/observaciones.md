# Observaciones - LORENZO

## Problemas encontrados:

### 1. Main.java - Líneas 1-17
- **Problema**: El archivo Main.java no implementa la funcionalidad requerida, solo contiene código de ejemplo
- **Líneas**: Todo el contenido del archivo debe ser reemplazado con la implementación del menú y lógica de la aplicación

### 2. Modelo Donation.java - Línea 35
- **Problema**: El constructor asigna incorrectamente el estado inicial como ASSIGNED en lugar de RECEIVED
- **Línea**: `this.status = DonationStatus.ASSIGNED;`

### 3. Modelo Assignment.java - Líneas 1-32
- **Problema**: Falta la relación bidireccional con Donation y getters/setters necesarios
- **Líneas**: No hay métodos para acceder a los campos de la entidad

### 4. Service ONG.java - Línea 42
- **Problema**: Lógica incorrecta en la validación del estado de la donación
- **Línea**: `if (donation.getStatus() == DonationStatus.RECEIVED) return new Result(false, "Donation already received");`
- **Corrección**: Debería verificar si NO está en estado RECEIVED

### 5. Service ONG.java - Línea 48
- **Problema**: Cambia el estado a RECEIVED cuando debería ser ASSIGNED
- **Línea**: `donation.setStatus(DonationStatus.RECEIVED);`

### 6. Service ONG.java - Líneas 75-76
- **Problema**: Ordenamiento incorrecto en la consulta - ordena por amount en lugar de total_amount
- **Líneas**: `cq.orderBy(cb.desc(root.get("amount")));`

### 7. Service ONG.java - Líneas 95-96
- **Problema**: Comparación incorrecta con strings en lugar de enums
- **Líneas**: `cb.equal(root.get("status"), "RECEIVED")` y `cb.equal(root.get("status"), "ASSIGNED")`

### 8. Service ONG.java - Línea 100
- **Problema**: Variable mal nombrada - debería ser totalAmount en lugar de donationCount
- **Línea**: `Expression<BigDecimal> donationCount = cb.sum(root.<BigDecimal>get("amount"));`

### 9. Tests ONGTest.java - Línea 25
- **Problema**: Las donaciones se crean con estado ASSIGNED por defecto, lo cual es incorrecto
- **Línea**: Las donaciones se persisten sin establecer el estado correcto

### 10. Tests ONGTest.java - Líneas 70-75
- **Problema**: Los tests esperan que las donaciones estén en estado ASSIGNED cuando deberían estar en RECEIVED
- **Líneas**: Los assertions verifican countAssigned = 1 cuando debería ser countReceived = 1 