import CategoryItem from './CategoryItem';
import Card from '../Card/Card'
import useCategory from '../../hooks/useCategory';
import { Link } from 'react-router-dom';


const Categories = ({ displayCategories }) => {
    const{ categories } = useCategory()

  return (
    <div className={ displayCategories ? "dipsplay-none" : "div" } >
        <div className="category-inner-div">
        <h4>PRODUCT CATEGORIES</h4>
        <div className="cat-div">
                {
                  categories.map((category, index) => 
                    <Link to={`/categories/viewByCategory/${category.id}`} key={ index }>
                      <CategoryItem category={ category }/>
                    </Link>
                  )
                }
        </div>
      </div>

      {/* <div className="card-div">
        <Card title="NEW ARRIVAL" pName="WOODEN" image="../images/wooden.png" />
      </div> */}
    </div>
  );
};

export default Categories;
