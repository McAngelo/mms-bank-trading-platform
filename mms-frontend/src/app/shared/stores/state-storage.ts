export const getThisState = (stateName: string) => {
  try {
    const serializedState = sessionStorage.getItem(stateName);
    if (serializedState === null || serializedState == '') {
      return undefined;
    }
    return JSON.parse(serializedState);
  } catch (err) {
    return undefined;
  }
};

export const getItem = (itemName: string) => {
  const items = getThisState(itemName);
  if (items === undefined) {
    return { result: [] };
  } else {
    return items;
  }
};

export const saveItem = (key: string, data: any) => {
  const serializedState = JSON.stringify(data);
  sessionStorage.setItem(key, serializedState);
};

export const getItemByKey = (key: string) => {
  try {
    const serializedState = sessionStorage.getItem(key);
    if (serializedState === null || serializedState == '') {
      return undefined;
    }
    return JSON.parse(serializedState);
  } catch (err) {
    return undefined;
  }
};

export const deleteItemByKey = (key: string) => sessionStorage.setItem(key, '');

export const emptyLocalStorage = (reducerkeys: any[] | undefined) => {
  try {
    if (undefined != reducerkeys && reducerkeys.length > 0) {
      reducerkeys.forEach((key: string) => {
        sessionStorage.setItem(key, '');
      });
    }
  } catch (err) {}
};

export const clearStorage = () => sessionStorage.clear();
