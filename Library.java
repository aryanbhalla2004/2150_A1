class KeyValuePair<K, V> {
  K key;
  V value;

  public KeyValuePair(K key, V value) {
      this.key = key;
      this.value = value;
  }


  @Override
  public String toString() {
      return key + " -> " + value;
  }
}