import './product.css'
import { DownOutlined } from '@ant-design/icons';
import { Dropdown, Space } from 'antd'
import useProduct from '../../hooks/useProduct';
import Categories from '../../components/CategoryCard/Categories';
import Pagination from './Pagination';
import FavoritePagination from '../Favorites/FavoritePagination';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';


const items = [
    {
      label: 'Sort By Name',
      key: '1',
    },
    {
      label: 'Sort By Image',
      key: '2',
    },
    {
      label: 'Sort By Price: Low to High',
      key: '3',
    },  
      {
      label: 'Sort By Price: High to Low',
      key: '4',
    },  
  ];

const Product = ({title, url, displayCategories, productUrlProp, isEditable, isId, showFavorites}) => {
    const params = useParams()
    const { products, setProducts, pageElementSize, 
          pageNumber, totalElements, numOfElements, setFavorites, favorites,
          favPageElementSize, favNumOfElements, favPageNumber, favTotalElements,
          setProductUrl, setFavoriteUrl } = useProduct()

    const onClick = ({ key }) => {
      if(!showFavorites){
        switch(key) {
            case '1':
                setProducts([...products.sort((a, b) => (a.name > b.name) ? 1 : -1)])
                return
          case '2':
                setProducts([...products.sort((a, b) => (a.imageUrl > b.imageUrl) ? 1 : -1)])
                return
            case '3':
                setProducts([...products.sort((a, b) => a.price - b.price)])
                return   
            case '4':
                setProducts([...products.sort((a, b) => b.price - a.price)])
                return
            default: 
                return products
        }
      }

        else{
          switch(key) {
            case '1':
                setFavorites([...favorites.sort((a, b) => (a.name > b.name) ? 1 : -1)])
                return
          case '2':
                setFavorites([...favorites.sort((a, b) => (a.imageUrl > b.imageUrl) ? 1 : -1)])
                return
            case '3':
                setFavorites([...favorites.sort((a, b) => a.price - b.price)])
                return   
            case '4':
                setFavorites([...favorites.sort((a, b) => b.price - a.price)])
                return
            default: 
                return products
        }
      }
    };


    const onClickFav = ({ key }) => {

  };

    useEffect(() => {
      showFavorites ? 
        setFavoriteUrl(productUrlProp)
        : 
        setProductUrl(isId ? `${productUrlProp}/${params.id}/paginated-all` : productUrlProp)
    }, [productUrlProp, setProductUrl, isId, params.id, showFavorites, setFavoriteUrl])

  return (
    <section className="favorites-section">
        <div className="products-info">
             {
              showFavorites ? 
              favNumOfElements < pageElementSize ?
              <p>Showing { (favPageNumber * favPageElementSize) + 1 } - { favTotalElements } of { favTotalElements }</p> :
              <p>Showing { favNumOfElements * (favPageNumber) + 1 } - { favNumOfElements * (favPageNumber + 1) } 
                  of {favTotalElements} results</p>
              :
              numOfElements < pageElementSize ?
              <p>Showing { (pageNumber * pageElementSize) + 1 } - { totalElements } of { totalElements }</p> :
              <p>Showing { numOfElements * (pageNumber) + 1 } - { numOfElements * (pageNumber + 1) } of {totalElements} results</p>

            }
            <h1>{ title }</h1>

          <Dropdown menu={{ items, onClick,}}>
            <a onClick={(e) => e.preventDefault()} href={"sorting"} id="dropdown-link">
                <Space>
                    Default Sorting
                    <DownOutlined />
                </Space>
                </a>
          </Dropdown>
            
        </div>
        
        <div className="encompassing-div">
          <Categories displayCategories={displayCategories} />
          {showFavorites ? 
            <FavoritePagination url={url} isEditable={isEditable} /> :
            <Pagination url={url} isEditable={isEditable} />
          }
      </div>
    </section>
  )
}

export default Product