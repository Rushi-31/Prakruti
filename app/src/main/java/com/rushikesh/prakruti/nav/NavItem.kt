package com.rushikesh.prakruti.nav

import com.dyneshwari.nav.Item


sealed class NavItem {
    object FinalResult :
        Item(
            path = NavPath.FINALRESULT.toString(),
        )

    object UserData :
        Item(
            path = NavPath.USERINFO.toString(),
        )

    object Qna :
        Item(
            path = NavPath.QNA.toString(),
        )

}