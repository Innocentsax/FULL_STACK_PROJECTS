import { createContext, useState } from "react";

const FavouriteContext = createContext({
  favourite: [],
  totalFavourites: 0,
  addToFavourite: (favouriteProduct) => {},
  removeFavourite: (favouriteId) => {},
  isInFavourite: (favouriteId) => {},
});
export function FavouriteContextProvider(props) {
  const [userFavourite, setUserFavourite] = useState([]);

  function addToFavoriteHandler(favouriteProduct) {
    setUserFavourite((prevuserFavourite) => {
      return prevuserFavourite.concat(favouriteProduct);
    });
  }

  function removeFromFavouriteHandler(productId) {
    setUserFavourite((prevuserFavourite) => {
      return prevuserFavourite.filter((product) => product.id !== productId);
    });
  }
  function itemIsInFavouriteHandler(productId) {
    return userFavourite.some((product) => product.id === productId);
  }
  const context = {
    favourite: userFavourite,
    totalFavourites: userFavourite.length,
    addToFavourite: addToFavoriteHandler,
    removeFavourite: removeFromFavouriteHandler,
    isInFavourite: itemIsInFavouriteHandler,
  };
  return (
    <FavouriteContext.Provider value={context}>
      {props.children}
    </FavouriteContext.Provider>
  );
}
export default FavouriteContext;
