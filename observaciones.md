# Observaciones - Preparcial2-Prog2-Lorenzo

## Problemas Encontrados

### 1. Error en Constructor de Donation (línea 38-44)
**Archivo:** `src/main/java/org/ong/models/Donation.java`
- **Problema:** El constructor asigna `DonationStatus.ASSIGNED` como estado inicial, pero según el enunciado debe ser `RECEIVED`
- **Línea 44:** `this.status = DonationStatus.ASSIGNED;`
- **Corrección:** Debe ser `this.status = DonationStatus.RECEIVED;`

### 2. Error en Lógica de Asignación (línea 47-48)
**Archivo:** `src/main/java/org/ong/service/ONG.java`
- **Problema:** La condición está invertida. Verifica si el estado es `RECEIVED` pero retorna error, cuando debería permitir la asignación
- **Línea 47-48:** `if (donation.getStatus() == DonationStatus.RECEIVED) return new Result(false, "Donation already received");`
- **Corrección:** Debe ser `if (donation.getStatus() != DonationStatus.RECEIVED) return new Result(false, "Donation is not in RECEIVED status");`

### 3. Error en Cambio de Estado (línea 52)
**Archivo:** `src/main/java/org/ong/service/ONG.java`
- **Problema:** Cambia el estado a `RECEIVED` cuando debería cambiarlo a `ASSIGNED`
- **Línea 52:** `donation.setStatus(DonationStatus.RECEIVED);`
- **Corrección:** Debe ser `donation.setStatus(DonationStatus.ASSIGNED);`

### 4. Error en Consulta de Estado (líneas 85-86 y 91-92)
**Archivo:** `src/main/java/org/ong/service/ONG.java`
- **Problema:** Compara con strings "RECEIVED" y "ASSIGNED" en lugar de usar los enums
- **Líneas 85-86:** `cb.equal(root.get("status"), "RECEIVED")`
- **Líneas 91-92:** `cb.equal(root.get("status"), "ASSIGNED")`
- **Corrección:** Debe usar `DonationStatus.RECEIVED` y `DonationStatus.ASSIGNED`

### 5. Error en Tests (línea 67)
**Archivo:** `src/test/java/org/ong/service/ONGTest.java`
- **Problema:** El test espera que las donaciones estén en estado `ASSIGNED` por defecto, pero según el enunciado deben estar en `RECEIVED`
- **Línea 67:** `Assertions.assertEquals(1, res.get(0).getCountAssigned());`
- **Corrección:** Los tests deben reflejar el comportamiento correcto según el enunciado

### 6. Falta de Validaciones
**Archivo:** `src/main/java/org/ong/service/ONG.java`
- **Problema:** No valida que los parámetros de entrada no sean null o vacíos
- **Líneas:** Constructor de `newDonation` y `assignDonation`
- **Mejora:** Agregar validaciones para `donorName`, `amount`, `category`, etc.

### 7. Manejo de Transacciones
**Archivo:** `src/main/java/org/ong/service/ONG.java`
- **Problema:** En `assignDonation` se hace una consulta en una transacción y luego otra operación en otra transacción
- **Líneas:** 40-44 y 50-56
- **Mejora:** Realizar toda la operación en una sola transacción 