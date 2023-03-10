package com.flow.hugh

import com.flow.hugh.data.Country
import com.flow.hugh.data.Genre

object Code {

    fun getGenre(codeOrName: String): Genre?
        = genreList.find { it.code == codeOrName || it.name == codeOrName }

    fun getCountry(codeOrName: String): Country?
        = countryList.find { it.code == codeOrName || it.name == codeOrName }

    val genreList = listOf(
        Genre("1", "드라마"),
        Genre("2", "판타지"),
        Genre("3", "서부"),
        Genre("4", "공포"),
        Genre("5", "로맨스"),
        Genre("6", "모험"),
        Genre("7", "스릴러"),
        Genre("8", "느와르"),
        Genre("9", "컬트"),
        Genre("10", "다큐멘터리"),
        Genre("11", "코미디"),
        Genre("12", "가족"),
        Genre("13", "미스터리"),
        Genre("14", "전쟁"),
        Genre("15", "애니메이션"),
        Genre("16", "범죄"),
        Genre("17", "뮤지컬"),
        Genre("18", "SF"),
        Genre("19", "액션"),
        Genre("20", "무협"),
        Genre("21", "에로"),
        Genre("22", "서스펜스"),
        Genre("23", "서사"),
        Genre("24", "블랙코미디"),
        Genre("25", "실험"),
        Genre("26", "영화카툰"),
        Genre("27", "영화음악"),
        Genre("28", "영화패러디포스터"),
    )

    val countryList = listOf(
        Country("FR","프랑스"),
        Country("GB","영국"),
        Country("HK","홍콩"),
        Country("JP","일본"),
        Country("KR","한국"),
        Country("US","미국"),
        Country("ETC","기타"),
    )
}