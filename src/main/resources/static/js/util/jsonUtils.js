const evil = (fn) => {
  const Fn = Function
  try {
    return new Fn('return ' + fn)()
  } catch (error) {
    throw error
  }
}
