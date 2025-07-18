package it.univaq.soccerfields.domain.use_case

import it.univaq.soccerfields.common.Resource
import it.univaq.soccerfields.domain.model.Field
import it.univaq.soccerfields.domain.repository.FieldLocalRepository
import it.univaq.soccerfields.domain.repository.FieldRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetFieldsUseCase @Inject constructor(
    private val remoteRepository: FieldRemoteRepository,
    private val localRepository: FieldLocalRepository,
) {

    operator fun invoke() : Flow<Resource<List<Field>>> {
        return flow {
            emit(Resource.Loading("Loading..."))

            localRepository.getAll()
                .catch {
                    emit(Resource.Error("Error: data not found in local database"))
                }
                .collect{ list ->
                    if(list.isEmpty()){
                        try {
                            val data = remoteRepository.getFields()
                            localRepository.insert(data)
                            emit(Resource.Success(data))
                        } catch (e: HttpException){
                            e.printStackTrace()
                            emit(Resource.Error("Error from server"))
                        }
                    } else {
                        emit(Resource.Success(list))
                    }

                }
        }
    }
}