package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // User FetchType.LAZY 설정 (= 지연 로딩)
    // 쿼리에 직접 FETCH JOIN 활용 => Todo 조회 할 경우 User도 LEFT OUTER JOIN으로 함께 조회된다.
    // 한번에 조회되어 영속성 컨텍스트에 저장된다.
    // 이후 User 조회시 영속성 컨텍스트 안에서 찾을 수 있다. (= 추가 쿼리 발생 X => N+1 문제 해결)
    // 페이지 객체를 다룰 경우 문제가 생길 수 있음
//    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")

    // EntityGraph로 연결하여 JPA가 FETCH JOIN 하도록 한다.
    // EntityGraph 사용으로 페이지 문제 해결
    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT t FROM Todo t ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN FETCH t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

    int countById(Long todoId);
}
