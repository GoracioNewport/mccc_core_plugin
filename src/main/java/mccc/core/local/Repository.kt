package mccc.core.local

import jetbrains.exodus.env.Environment
import jetbrains.exodus.env.Environments


final class Repository {

    private var games: Environment = Environments.newInstance("/local/data/.Games") // Our servers will be running linux, so ideally this path should be changed to something that linux systems can handle


}