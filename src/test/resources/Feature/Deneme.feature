Feature: Beymen arama ve sepet testi

  Scenario: Ürün arama, sepete ekleme ve kontrol işlemleri
    And Anasayfanin Acildigi Kontrol Edilir
    When Arama kutusuna Excel'den gelen "şort" kelimesi girilir
    And Arama kutusu temizlenir
    And Arama kutusuna Excel'den gelen "gömlek" kelimesi girilir
    And Enter tuşuna basılır
    Then Rastgele bir ürün seçilir
    And Ürün bilgisi txt dosyasına yazılır
    And Ürün sepete eklenir
    And Sepetteki fiyat ile ürün fiyatı karşılaştırılır
    And Ürün adedi 2 yapılır ve doğrulanır
    And Ürün sepetten silinir ve sepetin boş olduğu doğrulanır


