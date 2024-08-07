package sunil.springframework.spring6restmvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sunil.springframework.spring6restmvc.entities.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
