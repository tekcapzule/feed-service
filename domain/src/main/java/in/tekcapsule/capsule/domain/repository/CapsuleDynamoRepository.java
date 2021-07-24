package in.tekcapsule.capsule.domain.repository;

import in.devstream.core.domain.CrudRepository;
import in.tekcapsule.capsule.domain.model.Mentor;
import in.tekcapsule.capsule.domain.query.SearchItem;

import java.util.List;

public interface CapsuleDynamoRepository extends CrudRepository<Mentor, String> {

    void disableById(String tenantId, String id);
    List<SearchItem> search(String tenantId);
}
