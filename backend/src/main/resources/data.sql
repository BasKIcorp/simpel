INSERT INTO material (name, collector, shut_off_valves, check_valves, pressure_switch, pressure_sensors, plugs_or_flanges, rack, frame_base, pump_housing, external_casing) VALUES
('SV_10_15_22SV', 'Нержавеющая сталь', 'Никелированная латунь', 'Латунь, бронза', 'Хромированный цинковый сплав', 'AISI 316', 'Нержавеющая сталь', 'Окрашенная сталь', 'Окрашенная сталь', 'Нержавеющая сталь', 'Нержавеющая сталь'),
('SV_33_125SV', 'Нержавеющая сталь', 'Полиамид', 'Окрашенный чугун, заслонки из нержавеющей стали', 'Хромированный цинковый сплав', 'AISI 316', 'Нержавеющая сталь', 'Окрашенная сталь', 'Окрашенная сталь', 'Чугун', 'Нержавеющая сталь'),
('NSCS', 'Нержавеющая сталь', 'Полиамид', 'Окрашенный чугун, заслонки из нержавеющей стали', 'Хромированный цинковый сплав', 'AISI 316', 'Нержавеющая сталь', 'Окрашенная сталь', 'Окрашенная сталь', 'Чугун', 'Чугун'),
('VM', 'Нержавеющая сталь', 'Латунь', 'Латунь, бронза', 'Хромированный цинковый сплав', 'AISI 316', 'Нержавеющая сталь', 'Окрашенная сталь', 'Окрашенная сталь', 'Чугун', 'Нержавеющая сталь'),
('LNE', 'Нержавеющая сталь', 'Латунь', 'Латунь, бронза', 'Хромированный цинковый сплав', 'AISI 316', 'Нержавеющая сталь', 'Окрашенная сталь', 'Окрашенная сталь', 'Чугун', 'Чугун'),
('INM', 'Нержавеющая сталь', 'Чугун', 'Чугун', 'Хромированный цинковый сплав', 'AISI 316', 'Нержавеющая сталь', 'Окрашенная сталь', 'Окрашенная сталь', 'Чугун', 'Чугун'),
('NMM', 'Нержавеющая сталь', 'Чугун', 'Чугун', 'Хромированный цинковый сплав', 'AISI 316', 'Нержавеющая сталь', 'Окрашенная сталь', 'Окрашенная сталь', 'Чугун', 'Чугун'),
('BL', 'Нержавеющая сталь', 'Чугун', 'Чугун', 'Хромированный цинковый сплав', 'AISI 316', 'Нержавеющая сталь', 'Окрашенная сталь', 'Окрашенная сталь', 'Чугун', 'Чугун');
--
-- INSERT INTO engine (id,manufacturer, execution, pump_type, power, amperage, voltage, turnovers, type_of_protection, insulation_class, color)
-- VALUES
--     (1,'Manufacturer A', 'Execution A', 0, 5.5, 10.0, 380.0, 1500, 'IP65', 'B', 'Blue');
-- INSERT INTO pump (id,name, type, manufacturer, speed, number_of_steps, maximum_pressure, maximum_head, diameter, article, price, efficiency, NPSH, DM_in, DM_out, installation_length, description, material_name, engine_id)
-- VALUES
--     (1,'Pump A', 0, 'Manufacturer A', 2900, 2, 20, 10.5, 0, 'PUMP001', 1500.00, 85, 3.0, 0.5, 0.3, 1.0, 'Description of Pump A', 'BL', 1);
--
-- INSERT INTO gminstallation (id,type_installations, temperature, count_main_pumps, count_spare_pumps, flow_rate, pressure, control_type, power_type, diameter, name, coolant_type, subtype)
-- VALUES
--     (3,'GM', 50, 2, 1, 50, 10, null, null, null, 'Example Installation','WATER','RELAY_CONTROL');
-- INSERT INTO point_pressure (pumps_id, x, y) VALUES (1, 0.0, 12.0);
-- INSERT INTO point_pressure (pumps_id, x, y) VALUES (1, 5.0, 11.2);
-- INSERT INTO point_pressure (pumps_id, x, y) VALUES (1, 6.0, 10.9);
-- INSERT INTO point_pressure (pumps_id, x, y) VALUES (1, 8.0, 9.9);
-- INSERT INTO point_pressure (pumps_id, x, y) VALUES (1, 10.2, 8.3);
-- INSERT INTO point_pressure (pumps_id, x, y) VALUES (1, 11.0, 7.6);
-- INSERT INTO point_pressure (pumps_id, x, y) VALUES (1, 14.0, 4.3);
--
-- INSERT INTO point_power (pumps_id, x, y) VALUES (1, 0.0, 12.0);
-- INSERT INTO point_power (pumps_id, x, y) VALUES (1, 5.0, 11.2);
-- INSERT INTO point_power (pumps_id, x, y) VALUES (1, 6.0, 10.9);
-- INSERT INTO point_power (pumps_id, x, y) VALUES (1, 8.0, 9.9);
-- INSERT INTO point_power (pumps_id, x, y) VALUES (1, 10.2, 8.3);
-- INSERT INTO point_power (pumps_id, x, y) VALUES (1, 11.0, 7.6);
-- INSERT INTO point_power (pumps_id, x, y) VALUES (1, 14.0, 4.3);
--
-- INSERT INTO PointNPSH (pumps_id, x, y) VALUES (1, 0.0, 12.0);
-- INSERT INTO PointNPSH (pumps_id, x, y) VALUES (1, 5.0, 11.2);
-- INSERT INTO PointNPSH (pumps_id, x, y) VALUES (1, 6.0, 10.9);
-- INSERT INTO PointNPSH (pumps_id, x, y) VALUES (1, 8.0, 9.9);
-- INSERT INTO PointNPSH (pumps_id, x, y) VALUES (1, 10.2, 8.3);
-- INSERT INTO PointNPSH (pumps_id, x, y) VALUES (1, 11.0, 7.6);
-- INSERT INTO PointNPSH (pumps_id, x, y) VALUES (1, 14.0, 4.3);
--
-- SELECT *
-- FROM gminstallation
-- WHERE type_installations = 'GM'
--   AND subtype = 'RELAY_CONTROL'
--   AND coolant_type ='WATER'
--   AND temperature = 50
--   AND count_main_pumps = 2
--   AND count_spare_pumps = 1
--   AND flow_rate BETWEEN 0 AND 100;
--
-- select * from gminstallation;
