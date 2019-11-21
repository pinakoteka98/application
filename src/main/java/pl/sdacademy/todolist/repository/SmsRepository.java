package pl.sdacademy.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sdacademy.todolist.entity.Sms;

@Repository
public interface SmsRepository extends JpaRepository <Sms, Long> {
}
