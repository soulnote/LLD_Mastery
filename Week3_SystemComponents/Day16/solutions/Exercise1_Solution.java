/**
 * SOLUTION: Hospital Management ER Diagram
 */

public class Exercise1_Solution {
    
    /*
     * HOSPITAL MANAGEMENT SYSTEM - ER DIAGRAM
     * ======================================
     * 
     * ENTITIES:
     * ----------
     * 1. Person(person_id, first_name, last_name, dob, gender, phone, email, address)
     *    - This is a supertype for Patients, Doctors, Nurses
     * 
     * 2. Patient(patient_id FK -> Person, insurance_info, blood_type, emergency_contact)
     * 
     * 3. Doctor(doctor_id FK -> Person, department_id FK, specialization, license_number, years_of_exp)
     * 
     * 4. Nurse(nurse_id FK -> Person, department_id FK, certification, shift_timing)
     * 
     * 5. Department(department_id, name, head_doctor_id, location, phone)
     * 
     * 6. Ward(ward_id, ward_number, department_id FK, total_beds, occupied_beds, ward_type)
     * 
     * 7. Room(room_id, ward_id FK, room_number, bed_number, is_occupied)
     * 
     * 8. Appointment(appointment_id, patient_id FK, doctor_id FK, appointment_date, 
     *                status, notes, duration)
     * 
     * 9. MedicalRecord(record_id, patient_id FK, doctor_id FK, appointment_id FK,
     *                  diagnosis, symptoms, treatment, created_at)
     * 
     * 10. Prescription(prescription_id, record_id FK, medication_id FK, dosage, 
     *                  frequency, duration, instructions)
     * 
     * 11. Medication(medication_id, name, manufacturer, unit_price, stock_quantity)
     * 
     * 12. Admission(admission_id, patient_id FK, ward_id FK, admission_date, 
     *              discharge_date, reason, status)
     * 
     * RELATIONSHIPS:
     * ---------------
     * Person 1 ---- 1 Patient (specialization)
     * Person 1 ---- 1 Doctor (specialization)
     * Person 1 ---- 1 Nurse (specialization)
     * Department 1 ---- * Doctor
     * Department 1 ---- * Nurse
     * Department 1 ---- * Ward
     * Ward 1 ---- * Room
     * Patient 1 ---- * Appointment
     * Doctor 1 ---- * Appointment
     * Patient 1 ---- * MedicalRecord
     * Doctor 1 ---- * MedicalRecord
     * MedicalRecord 1 ---- * Prescription
     * Prescription * ---- 1 Medication
     * Patient 1 ---- * Admission
     * Admission * ---- 1 Ward
     */
    
    public static void main(String[] args) {
        System.out.println("📝 Hospital Management ER Diagram - SOLUTION\n");
        
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║          HOSPITAL MANAGEMENT SYSTEM - ER DIAGRAM                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("👤 PEOPLE ENTITIES:");
        System.out.println("------------------");
        System.out.println("• Person (supertype: Patient, Doctor, Nurse)");
        System.out.println("• Patient(patient_id, insurance, blood_type)");
        System.out.println("• Doctor(doctor_id, specialization, license)");
        System.out.println("• Nurse(nurse_id, certification, shift)");
        
        System.out.println("\n🏥 FACILITY ENTITIES:");
        System.out.println("---------------------");
        System.out.println("• Department(department_id, name, location)");
        System.out.println("• Ward(ward_id, department_id, beds)");
        System.out.println("• Room(room_id, ward_id, bed_number)");
        
        System.out.println("\n📋 CLINICAL ENTITIES:");
        System.out.println("---------------------");
        System.out.println("• Appointment(appointment_id, patient_id, doctor_id, date, status)");
        System.out.println("• MedicalRecord(record_id, patient_id, doctor_id, diagnosis)");
        System.out.println("• Prescription(prescription_id, record_id, medication, dosage)");
        System.out.println("• Medication(medication_id, name, stock)");
        System.out.println("• Admission(admission_id, patient_id, ward_id, dates)");
        
        System.out.println("\n🔗 KEY RELATIONSHIPS:");
        System.out.println("---------------------");
        System.out.println("• Doctor * → * Appointment ← * Patient");
        System.out.println("• Patient 1 → * MedicalRecord ← 1 Doctor");
        System.out.println("• Department 1 → * Ward 1 → * Room");
        
        System.out.println("\n✅ ER diagram design complete!");
    }
}
