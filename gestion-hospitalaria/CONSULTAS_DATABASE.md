# 📊 Consultas de Base de Datos - Sistema de Gestión Hospitalaria

Este documento contiene todas las consultas SQL necesarias para trabajar con la base de datos del Sistema de Gestión Hospitalaria en MySQL Admin.

## 📁 Archivos Disponibles

### 1. `database_setup.sql`
**Propósito**: Script completo de configuración de la base de datos
- ✅ Creación de la base de datos `hospital_db`
- ✅ Creación de todas las tablas con sus relaciones
- ✅ Índices para optimización
- ✅ Datos de prueba iniciales
- ✅ Verificaciones de integridad

### 2. `consultas_mysql_admin.sql`
**Propósito**: Consultas específicas para visualizar datos en MySQL Admin
- ✅ Consultas de verificación general
- ✅ Consultas por módulo (Pacientes, Médicos, Citas, etc.)
- ✅ Consultas de integridad de datos
- ✅ Consultas de mantenimiento

### 3. `datos_prueba_completos.sql`
**Propósito**: Datos de prueba extensos para testing
- ✅ Más especialidades médicas
- ✅ Más médicos con especialidades
- ✅ Más pacientes con historias clínicas
- ✅ Más citas, consultas y diagnósticos
- ✅ Más habitaciones y hospitalizaciones

### 4. `reportes_estadisticas.sql`
**Propósito**: Reportes y estadísticas del sistema
- ✅ Reporte de ocupación hospitalaria
- ✅ Estadísticas de citas médicas
- ✅ Análisis de pacientes por edad
- ✅ Reportes de médicos y especialidades
- ✅ Análisis de diagnósticos y medicamentos
- ✅ Dashboard principal

## 🚀 Cómo Usar en MySQL Admin

### Paso 1: Configurar la Base de Datos
```sql
-- Ejecutar en MySQL Admin
SOURCE database_setup.sql;
```

### Paso 2: Verificar la Instalación
```sql
-- Verificar que todas las tablas se crearon
SHOW TABLES;

-- Verificar datos insertados
SELECT 'PACIENTES' as tabla, COUNT(*) as total FROM paciente
UNION ALL
SELECT 'MÉDICOS', COUNT(*) FROM medico
UNION ALL
SELECT 'ESPECIALIDADES', COUNT(*) FROM especialidad;
```

### Paso 3: Insertar Datos Adicionales (Opcional)
```sql
-- Para más datos de prueba
SOURCE datos_prueba_completos.sql;
```

### Paso 4: Ejecutar Consultas de Visualización
```sql
-- Consultas básicas
SOURCE consultas_mysql_admin.sql;

-- Reportes y estadísticas
SOURCE reportes_estadisticas.sql;
```

## 📋 Estructura de la Base de Datos

### Tablas Principales
1. **paciente** - Información de pacientes
2. **historia_clinica** - Historial médico
3. **antecedente_medico** - Antecedentes específicos
4. **medico** - Información de médicos
5. **especialidad** - Especialidades médicas
6. **medico_especialidad** - Relación médico-especialidad
7. **cita** - Citas médicas programadas
8. **consulta** - Consultas realizadas
9. **diagnostico** - Diagnósticos médicos
10. **receta_medica** - Recetas médicas
11. **detalle_receta** - Detalles de medicamentos
12. **habitacion** - Habitaciones del hospital
13. **hospitalizacion** - Registros de hospitalización

## 🔍 Consultas Más Útiles

### Ver Todos los Pacientes
```sql
SELECT 
    id_paciente as 'ID',
    dni as 'DNI',
    CONCAT(nombres, ' ', apellidos) as 'Nombre Completo',
    fecha_nacimiento as 'Fecha Nacimiento',
    CASE sexo WHEN 'M' THEN 'Masculino' WHEN 'F' THEN 'Femenino' END as 'Sexo',
    CASE estado WHEN 1 THEN 'Activo' WHEN 0 THEN 'Inactivo' END as 'Estado'
FROM paciente
ORDER BY apellidos, nombres;
```

### Ver Citas del Día
```sql
SELECT 
    c.fecha as 'Fecha',
    c.hora as 'Hora',
    CONCAT(p.nombres, ' ', p.apellidos) as 'Paciente',
    CONCAT(m.nombres, ' ', m.apellidos) as 'Médico',
    c.motivo as 'Motivo',
    UPPER(c.estado) as 'Estado'
FROM cita c
JOIN paciente p ON c.id_paciente = p.id_paciente
JOIN medico m ON c.id_medico = m.id_medico
WHERE c.fecha = CURDATE()
ORDER BY c.hora;
```

### Ver Habitaciones Ocupadas
```sql
SELECT 
    h.numero as 'Habitación',
    h.tipo as 'Tipo',
    CONCAT(p.nombres, ' ', p.apellidos) as 'Paciente',
    p.dni as 'DNI',
    hosp.fecha_ingreso as 'Fecha Ingreso',
    hosp.diagnostico_ingreso as 'Diagnóstico'
FROM habitacion h
JOIN hospitalizacion hosp ON h.id_habitacion = hosp.id_habitacion
JOIN paciente p ON hosp.id_paciente = p.id_paciente
WHERE h.estado = 'ocupada' AND hosp.estado = 'activo'
ORDER BY h.tipo, h.numero;
```

### Ver Médicos por Especialidad
```sql
SELECT 
    e.nombre as 'Especialidad',
    COUNT(DISTINCT me.id_medico) as 'Cantidad Médicos',
    GROUP_CONCAT(CONCAT(m.nombres, ' ', m.apellidos) SEPARATOR ', ') as 'Médicos'
FROM especialidad e
LEFT JOIN medico_especialidad me ON e.id_especialidad = me.id_especialidad
LEFT JOIN medico m ON me.id_medico = m.id_medico
GROUP BY e.id_especialidad, e.nombre
ORDER BY COUNT(DISTINCT me.id_medico) DESC;
```

## 📊 Reportes Disponibles

### 1. Reporte de Ocupación Hospitalaria
- Estado actual de habitaciones
- Habitaciones ocupadas con información del paciente
- Porcentajes de ocupación por tipo

### 2. Reporte de Citas Médicas
- Citas por médico y estado
- Citas programadas para los próximos 7 días
- Estadísticas de citas

### 3. Reporte de Pacientes
- Distribución por rango de edad
- Pacientes más frecuentes
- Análisis demográfico

### 4. Reporte de Médicos
- Médicos más solicitados
- Médicos por especialidad
- Estadísticas de atención

### 5. Reporte de Diagnósticos
- Diagnósticos más frecuentes
- Diagnósticos por tipo
- Análisis de patrones

### 6. Reporte de Medicamentos
- Medicamentos más recetados
- Recetas por médico
- Análisis de prescripciones

### 7. Reporte de Hospitalizaciones
- Tiempo promedio de hospitalización
- Hospitalizaciones por mes
- Análisis de ocupación

## 🛠️ Consultas de Mantenimiento

### Verificar Integridad de Datos
```sql
-- Pacientes sin historias clínicas
SELECT p.id_paciente, CONCAT(p.nombres, ' ', p.apellidos) as 'Paciente'
FROM paciente p
LEFT JOIN historia_clinica hc ON p.id_paciente = hc.id_paciente
WHERE hc.id_historia IS NULL;
```

### Limpiar Datos de Prueba
```sql
-- CUIDADO: Solo usar en entorno de desarrollo
DELETE FROM detalle_receta;
DELETE FROM receta_medica;
DELETE FROM diagnostico;
DELETE FROM consulta;
DELETE FROM cita;
DELETE FROM hospitalizacion;
DELETE FROM antecedente_medico;
DELETE FROM historia_clinica;
DELETE FROM medico_especialidad;
DELETE FROM paciente;
DELETE FROM medico;
DELETE FROM especialidad;
DELETE FROM habitacion;
```

## 📈 Dashboard Principal

### Resumen Ejecutivo
```sql
SELECT 
    'PACIENTES' as 'Métrica',
    COUNT(*) as 'Total',
    SUM(CASE WHEN estado = 1 THEN 1 ELSE 0 END) as 'Activos'
FROM paciente
UNION ALL
SELECT 'MÉDICOS', COUNT(*), SUM(CASE WHEN estado = 1 THEN 1 ELSE 0 END) FROM medico
UNION ALL
SELECT 'CITAS HOY', COUNT(*), SUM(CASE WHEN estado = 'programada' THEN 1 ELSE 0 END) FROM cita WHERE fecha = CURDATE()
UNION ALL
SELECT 'HABITACIONES', COUNT(*), SUM(CASE WHEN estado = 'disponible' THEN 1 ELSE 0 END) FROM habitacion
UNION ALL
SELECT 'HOSPITALIZACIONES', COUNT(*), SUM(CASE WHEN estado = 'activo' THEN 1 ELSE 0 END) FROM hospitalizacion;
```

## 🔧 Configuración Recomendada

### Variables de MySQL
```sql
-- Para mejor rendimiento
SET GLOBAL innodb_buffer_pool_size = 256M;
SET GLOBAL max_connections = 200;
SET GLOBAL query_cache_size = 32M;
```

### Índices Adicionales (Opcional)
```sql
-- Para consultas frecuentes
CREATE INDEX idx_paciente_fecha_nacimiento ON paciente(fecha_nacimiento);
CREATE INDEX idx_cita_fecha_estado ON cita(fecha, estado);
CREATE INDEX idx_hospitalizacion_fecha_ingreso_estado ON hospitalizacion(fecha_ingreso, estado);
```

## 📞 Soporte

Si tienes problemas con las consultas o necesitas consultas adicionales, puedes:

1. Revisar los logs de MySQL para errores
2. Verificar que todas las tablas estén creadas correctamente
3. Comprobar que los datos de prueba se insertaron
4. Usar `EXPLAIN` para analizar el rendimiento de consultas complejas

---

**¡Disfruta explorando tu Sistema de Gestión Hospitalaria! 🏥**
