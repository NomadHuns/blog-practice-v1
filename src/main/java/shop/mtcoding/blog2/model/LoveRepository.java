package shop.mtcoding.blog2.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoveRepository {
        public List<Love> findAll();

        public Love findById(Love love);

        public int insert(Love love);

        public int deleteById(int id);
        
        public int updateById(Love love);

        public Love findByBoardIdAndUserId(Love love);
}
